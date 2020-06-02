#!/usr/bin/python
import subprocess

from flask import Flask, request

app = Flask("__name__")


@app.route("/help")
def hlp():
    return "direction - arrivals/departures <br>country - country name <br>city - city name<br>airport - airport " \
           "name<br>terminal - terminal " \
           "number<br>weekday - week day (Monday,Tuesday...etc) <br>start/end - date range bounds (" \
           "dd/MM/yyyy-HH:mm:ss)<br>airline - company name<br><br>Example " \
           "link:<br><a href=http://localhost:8000/?direction=&country=&city=&airport=&terminal=&weekday=&start=&end" \
           "=&airline=>http://localhost:8000/?direction=&country=&city=&airport=&terminal=&weekday=&start=&end" \
           "=&airline=</a> "


@app.route("/")
def airport():
    return subprocess.check_output(["java", "-classpath", "C:/Users/Elazar/Documents/GitHub/Y1S2-Homework/bin",
                                    "AirportProject.Program",
                                    request.args.get('direction'),  # direction
                                    request.args.get('country'),    # country
                                    request.args.get('city'),       # city
                                    request.args.get('airport'),    # airport
                                    request.args.get('terminal'),   # terminal
                                    request.args.get('sunday'),    # dayofweek
                                    request.args.get('monday'),    # dayofweek
                                    request.args.get('tuesday'),    # dayofweek
                                    request.args.get('wednesday'),    # dayofweek
                                    request.args.get('thursday'),    # dayofweek
                                    request.args.get('friday'),    # dayofweek
                                    request.args.get('saturday'),    # dayofweek
                                    request.args.get('start'),      # date range lower bound
                                    request.args.get('end'),        # date range higher bound
                                    request.args.get('airline')     # company
                                    ])


@app.route("/departures")
def departures():
    return subprocess.check_output(["java", "-classpath", "C:/Users/Elazar/Documents/GitHub/Y1S2-Homework/bin",
                                    "AirportProject.Program", "departures",
                                    request.args.get('country'),    # country
                                    request.args.get('city'),       # city
                                    request.args.get('airport'),    # airport
                                    request.args.get('terminal'),   # terminal
                                    request.args.get('weekday'),    # dayofweek
                                    request.args.get('start'),      # date range lower bound
                                    request.args.get('end'),        # date range higher bound
                                    request.args.get('airline')     # company
                                    ])


@app.route("/arrivals")
def arrivals():
    return subprocess.check_output(["java", "-classpath", "C:/Users/Elazar/Documents/GitHub/Y1S2-Homework/bin",
                                    "AirportProject.Program", "arrivals",
                                    request.args.get('country'),    # country
                                    request.args.get('city'),       # city
                                    request.args.get('airport'),    # airport
                                    request.args.get('terminal'),   # terminal
                                    request.args.get('weekday'),    # dayofweek
                                    request.args.get('start'),      # date range lower bound
                                    request.args.get('end'),        # date range higher bound
                                    request.args.get('airline')     # company
                                    ])


if __name__ == "__main__":
    app.run(port=8000, host="0.0.0.0", debug=True)

# http://localhost:8000/help
# http://localhost:8000/?direction=departures&country=&city=&airport=&terminal=&weekday=&start=&end=20/11/2020-22:22:22&airline=
# http://localhost:8000/?direction=&country=&city=&airport=&terminal=&weekday=&start=&end=&airline=

# REQUIRED FORMAT:
# http://localhost:8000/departures?country=france&city=marseille&airport=marseille&airline=elal&day1=2&month1=6&year1=2020&day2=31&month2=7&year2=2020&sun=true&tuesday=true
