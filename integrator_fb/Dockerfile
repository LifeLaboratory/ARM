FROM python:3

WORKDIR /usr/src/integrator

COPY requirements.txt ./
RUN pip install -r requirements.txt
COPY . .
#COPY ./../api .
CMD [ "python", "./recv_facebook_message.py" ]