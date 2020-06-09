#!/usr/bin/python
import subprocess

from flask import Flask, request

app = Flask("__name__")


@app.route("/")
def hlp():
    return "Example links: " \
           "<br><a href=http://localhost:8000/arrivals?outformat=html&country=&city=&airport=&airline=&day1=&month1" \
           "=&year1=&day2=&month2=&year2=&sunday=&monday=&tuesday=&wednesday=&thursday=&friday=&saturday=>Arrivals</a" \
           "><br><a href=http://localhost:8000/departures?outformat=html&country=&city=&airport=&airline=&day1" \
           "=&month1=&year1=&day2=&month2=&year2=&sunday=&monday=&tuesday=&wednesday=&thursday=&friday=&saturday=> " \
            "Departures</a>"


@app.route("/departures")
def departures():
    return subprocess.check_output(["java", "-classpath", "C:/Users/Elazar/Documents/GitHub/Y1S2-Homework/bin",
                                    "AirportProject.Program",
                                    request.args.get('outformat'),
                                    "departures",
                                    request.args.get('country'),  # country
                                    request.args.get('city'),  # city
                                    request.args.get('airport'),  # airport
                                    request.args.get('airline'),  # company
                                    request.args.get('day1'), request.args.get('month1'), request.args.get('year1'),
                                    request.args.get('day2'), request.args.get('month2'), request.args.get('year2'),
                                    request.args.get('sunday'), request.args.get('monday'),
                                    request.args.get('tuesday'), request.args.get('wednesday'),
                                    request.args.get('thursday'), request.args.get('friday'),
                                    request.args.get('saturday')])
    # request.args.get('terminal'),  # terminal


@app.route("/arrivals")
def arrivals():
    return subprocess.check_output(["java", "-classpath", "C:/Users/Elazar/Documents/GitHub/Y1S2-Homework/bin",
                                    "AirportProject.Program",
                                    request.args.get('outformat'),
                                    "arrivals",
                                    request.args.get('country'),  # country
                                    request.args.get('city'),  # city
                                    request.args.get('airport'),  # airport
                                    request.args.get('airline'),  # company
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
