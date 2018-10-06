import pandas as pd
from pprint import pprint


class Answer:
    """Класс для работы с предложениями решений проблем"""

    def __init__(self):
        """Загрузка датасета """

        df = pd.read_csv('messages_for_answer.csv',
                         sep=',', encoding='utf-8')
        self.msgs = df.values.tolist()

    @staticmethod
    def distance(a, b):
        """Расстояние между двумя массивами"""

        n, m = len(a), len(b)
        if n > m:
            a, b = b, a
            n, m = m, n
        current_row = range(n + 1)
        for i in range(1, m + 1):
            previous_row, current_row = current_row, [i] + [0] * n
            for j in range(1, n + 1):
                add, delete, change = previous_row[j] + 1, current_row[j - 1] + 1, previous_row[j - 1]
                if a[j - 1] != b[i - 1]:
                    change += 1
                current_row[j] = min(add, delete, change)
        return current_row[n]

    def get(self, msg=""):
        """Получение предложений сообщений"""

        minimums = [999999999, 999999999, 999999999, 999999999]
        pos = [-9999, -9999, -9999, -9999]
        answers = []
        dist = []
        for num, [_, Msg] in enumerate(self.msgs):
            dist.append(self.distance(msg, Msg))
        for num, D in enumerate(dist):
            if minimums[0] > D:
                minimums[0] = D
                pos[0] = num
            if minimums[1] > D and num != pos[0]:
                minimums[1] = D
                pos[1] = num
            if minimums[2] > D and num != pos[0] and num != pos[1]:
                minimums[2] = D
                pos[2] = num
            if minimums[3] > D and num != pos[0] and num != pos[1] and num != pos[2]:
                minimums[3] = D
                pos[3] = num
        answers.append(self.msgs[pos[0]])
        answers.append(self.msgs[pos[1]])
        answers.append(self.msgs[pos[2]])
        answers.append(self.msgs[pos[3]])
        # pprint(answers)
        return answers

    def add(self, msg="", answer=""):
        """Добавление сообщения и ответа в датасет"""

        data_file = open('messages_for_answer.csv', 'a')
        data_file.write("\n\"" + answer + "\",\"" + msg + "\"")
        data_file.close()
        self.msgs.append([answer, msg])


if __name__ == "__main__":
    ans = Answer()
    pprint(ans.get("Что делать, есть нет интернета?"))
