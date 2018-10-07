# -*- coding: utf-8 -*-
import time
import telebot

TELEGRAM_TOKEN = "675147545:AAFlp1N9U51cjF_Bgr5JlZIy4STg2TZsBd0"

bot = telebot.TeleBot(TELEGRAM_TOKEN)

@bot.message_handler(commands=['start', 'help'])
def send_welcome(message):
	bot.reply_to(message, "Howdy, how are you doing?")

@bot.message_handler(func=lambda message: True)
def echo_all(message):
	bot.reply_to(message, message.text)

bot.polling(none_stop=True)