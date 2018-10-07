import pandas as pd
import operator
import numpy as np
from keras.models import Model, model_from_json
from keras.layers import Dense, Input, LSTM, Embedding, SpatialDropout1D


class MsgClassification:
    """Класс для классификации сообщений"""

    def __init__(self):
        """Загрузка датасета """

        self.df = pd.read_csv('messages_with_type.csv',
                              sep=',', encoding='utf-8')
        self.df = self.df[:3000]
        self.periodicity = []
        self.mean = 80  # выравнивание длины предложений
        self.model = None

    def training(self):
        """Обучение сети"""

        self.periodicity = dict({'': 9999999})  # частоты слов
        types = []
        for Msg, Type in self.df.values.tolist():
            if type(Msg) == str:
                for word in Msg.split(" "):
                    if len(word) > 0:
                        if word in self.periodicity.keys():
                            self.periodicity.update({word: self.periodicity[word] + 1})
                        else:
                            self.periodicity.update({word: 1})
                types.append(Type)
        self.periodicity = [Key for Key, Value in sorted(self.periodicity.items(), key=operator.itemgetter(1))[::-1]]

        # перевод сообщений в частотное представление
        period_pred = []
        max_length = -99999
        lenghts = []
        for msg, type_msg in self.df.values.tolist():
            elem = []
            if type(msg) == str:
                for word in msg.split(" "):
                    elem.append(self.periodicity.index(word))
                period_pred.append((elem, type_msg))
                tmp_len = len(msg)
                lenghts.append(tmp_len)
                if max_length < tmp_len:
                    max_length = tmp_len
        types = []
        msgs = []
        for msg, type_msg in period_pred:
            while len(msg) < self.mean:
                msg.append(0)
            if len(msg) > self.mean:
                msg = msg[:-(len(msg) - self.mean)]
            types.append(type_msg)
            msgs.append(msg)

        inputs = Input(shape=(self.mean,))
        # x = Dense(1024, activation='tanh')(inputs)
        # x = Dense(512, activation='tanh')(x)
        # x = Dense(256, activation='tanh')(x)
        x = Embedding(50000, 32)(inputs)
        x = SpatialDropout1D(0.2)(x)
        x = LSTM(100, dropout=0.2, recurrent_dropout=0.2)(x)
        outputs = Dense(1, activation='tanh')(x)  # количество типов

        self.model = Model(inputs=inputs, outputs=outputs)
        self.model.compile(loss='mean_squared_error', optimizer='sgd')

        border = int(len(period_pred) * 0.7)

        x_train = np.array(msgs[:border])
        y_train = np.array(types[:border])
        x_test = np.array(msgs[border:])
        y_test = np.array(types[border:])

        self.model.fit(x_train, y_train, epochs=5, verbose=1, batch_size=128, validation_data=(x_test, y_test))

    def predict(self, msg=""):
        """Получение предсказания"""

        sentence = []
        for word in msg.split(" "):
            sentence.append(self.periodicity.index(word))
        while len(sentence) < self.mean:
            sentence.append(0)
        if len(sentence) > self.mean:
            sentence = sentence[:-(len(sentence) - self.mean)]
        result = self.model.predict(np.array([sentence]))
        if result < -0.25:
            return -1
        elif result > 0.25:
            return 1
        else:
            return 0

    def add(self, msg="", type_msg=""):
        data_file = open('messages_with_type.csv', 'a')
        data_file.write("\"" + msg + "\"," + str(type_msg) + "\n")
        data_file.close()

    def save(self):
        model_json = self.model.to_json()
        json_file = open("model_classification.json", "w")
        # Записываем архитектуру сети в файл
        json_file.write(model_json)
        json_file.close()
        # Записываем данные о весах в файл
        self.model.save_weights("model_classification.h5")
        with open('periodicity.w5', 'w') as f:
            for item in self.periodicity:
                f.write("%s\n" % item)

    def load(self):
        json_file = open("model_classification.json", "r")
        loaded_model_json = json_file.read()
        json_file.close()
        loaded_model = model_from_json(loaded_model_json)
        # Загружаем сохраненные веса в модель
        loaded_model.load_weights("model_classification.h5")
        self.model = loaded_model
        self.model.compile(loss='mean_squared_error', optimizer='sgd')
        self.periodicity = []
        with open('periodicity.w5', 'r') as f:
            for line in f:
                self.periodicity.append(line.replace("\n",""))


if __name__ == "__main__":
    classificator = MsgClassification()
    # classificator.training()
    # print(classificator.predict("Срочно ищет дом девочка."))
    # classificator.save()
    classificator.load()
    print(classificator.predict("Срочно ищет дом девочка."))

