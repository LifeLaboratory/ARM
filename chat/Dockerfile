FROM python:3

WORKDIR /usr/src/chat

COPY requirements.txt ./
RUN pip install -r requirements.txt
COPY . .
#COPY ./../api .
CMD [ "python", "./main.py" ]