FROM python:3

WORKDIR /usr/src/classification

COPY requirements.txt ./
RUN pip install -r requirements.txt
COPY . .
#COPY ./../api .
CMD [ "python", "./main.py" ]