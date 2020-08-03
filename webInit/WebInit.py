#!/usr/bin/python
import os
import subprocess

from flask import Flask, request

app = Flask(__name__)

path = os.path.abspath(os.path.join(os.getcwd(), os.pardir)) + "\\target\classes"


def get_url(direction):
    return "<a href=http://localhost:8000/" + direction.lower() + \
           "?outformat=html&country=&city=&airport=&airline=&day1=&month1=&year1=" \
           "&day2=&month2=&year2=&sunday=&monday=&tuesday=&wednesday=&thursday=&friday=&saturday=>" \
           + direction + "</a><br>"


@app.route("/")
def hlp():
    return "Example links:<br>" + get_url("Arrivals") + get_url("Departures") + get_url("All")


@app.route("/departures")
def departures():
    return all_flights("departures")


@app.route("/arrivals")
def arrivals():
    return all_flights("arrivals")


@app.route("/all")
def all_flights(direction="all"):
    return subprocess.check_output(["java", "-classpath", path,
                                    "AirportProject.Program",  # Package.Class_File
                                    request.args.get('outformat'),
                                    direction,
                                    request.args.get('country'),
                                    request.args.get('city'),
                                    request.args.get('airport'),
                                    request.args.get('airline'),
                                    request.args.get('day1'), request.args.get('month1'), request.args.get('year1'),
                                    request.args.get('day2'), request.args.get('month2'), request.args.get('year2'),
                                    request.args.get('sunday'), request.args.get('monday'),
                                    request.args.get('tuesday'), request.args.get('wednesday'),
                                    request.args.get('thursday'), request.args.get('friday'),
                                    request.args.get('saturday')])
    # request.args.get('terminal'),  # terminal


if __name__ == "__main__":
    app.run(port=8000, host="0.0.0.0", debug=True)

# http://localhost:8000/

# REQUIRED FORMAT:
# http://localhost:8000/arrivals?outformat=html&country=&city=&airport=&airline=&day1=&month1=&year1=&day2=&month2=&year2=&sunday=&monday=&tuesday=&wednesday=&thursday=&friday=&saturday=
# http://localhost:8000/departures?outformat=html&country=&city=&airport=&airline=&day1=&month1=&year1=&day2=&month2=&year2=&sunday=&monday=&tuesday=&wednesday=&thursday=&friday=&saturday=
# http://localhost:8000/departures?outformat=html&country=france&city=paris&airport=CDG&airline=elal&day1=4&month1=6&year1=2020&day2=31&month2=7&year2=2020&sunday=true&monday=false&tuesday=true&wednesday=true&thursday=false&friday=false&saturday=false

# command line example:
# curl "http://localhost:8000/all?outformat=text&country=&city=&airport=&airline=&day1=&month1=&year1=&day2=&month2=&
#       year2=&sunday=&monday=&tuesday=&wednesday=&thursday=&friday=&saturday="
#
