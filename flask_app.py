from flask import Flask
import jsonpickle
from artifacts import Hotel
from artifacts import Availability

app = Flask(__name__)
hotel_list = []
availability_list = []
hotels_from_txt = "artefatos/hoteis.txt"
availability_from_txt = "artefatos/disp.txt"

@app.route("/hotels")
def get_hotels_list():
    print "running /hotels"
    return jsonpickle.encode(hotel_list, unpicklable=False)

@app.route("/availability")
def get_availability():
    return jsonpickle.encode(availability_list, unpicklable=False)

@app.route("/hotel/<int:hotel_id>")
def get_hotel_by_id(hotel_id):
    return jsonpickle.encode(hotel_list[hotel_id - 1], unpicklable=False)

# preload hotels
with open(hotels_from_txt) as f:
    for content in f.readlines():
        hotel_id       = content.split(",")[0]
        hotel_location = content.split(",")[1]
        hotel_name     = content.split(",")[2].replace("\n", "")

        current_hotel = Hotel()
        current_hotel.id = hotel_id
        current_hotel.location = hotel_location
        current_hotel.name = hotel_name
        current_hotel.availability_days = []

        hotel_list.append(current_hotel)

# preload availability
with open(availability_from_txt) as f:
    for content in f.readlines():

        id           = content.split(",")[0]
        date         = content.split(",")[1]
        is_available = False
        if content.split(",")[2].replace("\n", "") == '1':
            is_available = True

        current_availability      = Availability()
        current_availability.id   = id
        current_availability.date = date
        current_availability.is_available = is_available

        availability_list.append(current_availability)

# get all availability and add at current hotel
for availability in availability_list:
    for hotel in hotel_list:
        if availability.id == hotel.id:
            hotel.availability_days.append(availability)

print ">>> Total of hotels: {0}".format(len(hotel_list))
print ">>> Total of availability: {0}".format(len(availability_list))
app.run()
