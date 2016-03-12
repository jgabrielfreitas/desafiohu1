from json import JSONEncoder
import json

class Hotel():

    id = 0
    location = ""
    name = ""
    availability_days = [1,2,3]

class Availability():
    id = 0
    date = ""
    is_available = False
