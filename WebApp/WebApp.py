#!/usr/bin/python
import os
import subprocess

from flask import Flask, request, render_template, flash, url_for
from forms import FlightsForm

app = Flask(__name__)

app.config['SECRET_KEY'] = '1074ef660db4d179d79feecf0ace6a51'

path = os.getcwd() + "/../target/classes"  # get parent of working dir then go to classes folder


def bool2str(boolean: bool):
    if boolean:
        return "true"
    return "false"


def display_results(flights=None):
    if flights is None:
        flights = []
    return render_template("results.html", title="Filtered Results", flights=flights)


def get_response_for_args(
        direction, country, city, airport, airline,
        day1, month1, year1,
        day2, month2, year2,
        sunday, monday, tuesday, wednesday, thursday, friday, saturday
        # ,terminal
):
    return display_results(
        flights=subprocess.check_output(
            ["java", "-classpath", path,
             "AirportProject.Program",  # Package.Class_File
             "HTML",
             direction, country, city, airport, airline,
             day1, month1, year1,
             day2, month2, year2,
             sunday, monday, tuesday, wednesday, thursday, friday, saturday
             # terminal
             ]).decode().split("<br>")  # decode() - decode bytes to string

    )


@app.route("/about")
def about():
    return render_template("about.HTML")


@app.route("/", methods=['GET', 'POST'])
def submit():
    form = FlightsForm()
    if form.validate_on_submit():
        flash(u'Filters Processed!', 'success')
        return get_response_for_args(
            direction=form.direction.data,
            country=form.country.data,
            city=form.city.data,
            airport=form.airport.data,
            airline=form.airline.data,
            day1=str(form.date1.data.day),
            month1=str(form.date1.data.month),
            year1=str(form.date1.data.year),
            day2=str(form.date2.data.day),
            month2=str(form.date2.data.month),
            year2=str(form.date2.data.year),
            sunday=bool2str(form.sunday.data),
            monday=bool2str(form.monday.data),
            tuesday=bool2str(form.tuesday.data),
            wednesday=bool2str(form.wednesday.data),
            thursday=bool2str(form.thursday.data),
            friday=bool2str(form.friday.data),
            saturday=bool2str(form.saturday.data)
        )
    return render_template('form.html', title='Submit Filters', form=form)


def get_url(direction):
    return "<a href=http://localhost:8000/" + direction.lower() + \
           "?outformat=html&country=&city=&airport=&airline=&day1=&month1=&year1=" \
           "&day2=&month2=&year2=&sunday=&monday=&tuesday=&wednesday=&thursday=&friday=&saturday=>" \
           + direction + "</a><br>"


def go_back(step=""):
    return "<a href=http://localhost:8000/" + step + ">Go Back</a><br><br>"


@app.route("/legacy")
def legacy():
    return go_back() + "Example links:<br>" + get_url("Arrivals") + get_url("Departures") + get_url("All")


@app.route("/departures")
def departures():
    return all_flights("departures")


@app.route("/arrivals")
def arrivals():
    return all_flights("arrivals")


@app.route("/all")
def all_flights(direction="all"):
    return go_back("legacy").encode() + \
           subprocess.check_output(["java", "-classpath", path,
                                    "AirportProject.Program",  # Package.Class_File
                                    request.args.get('outformat'),
                                    direction
                                    ] + [value for value in list(request.args.values())[1:]])


if __name__ == "__main__":
    app.run(port=8000, host="0.0.0.0", debug=True)
    app.add_url_rule('/favicon.ico', redirect_to=url_for('static', filename='favicon.ico'))

# http://localhost:8000/

# REQUIRED FORMAT:
# http://localhost:8000/arrivals?outformat=html&country=&city=&airport=&airline=&day1=&month1=&year1=&day2=&month2=&year2=&sunday=&monday=&tuesday=&wednesday=&thursday=&friday=&saturday=
# http://localhost:8000/departures?outformat=html&country=&city=&airport=&airline=&day1=&month1=&year1=&day2=&month2=&year2=&sunday=&monday=&tuesday=&wednesday=&thursday=&friday=&saturday=
# http://localhost:8000/departures?outformat=html&country=france&city=paris&airport=CDG&airline=elal&day1=4&month1=6&year1=2020&day2=31&month2=7&year2=2020&sunday=true&monday=false&tuesday=true&wednesday=true&thursday=false&friday=false&saturday=false

# command line example:
# curl "http://localhost:8000/all?outformat=text&country=&city=&airport=&airline=&day1=&month1=&year1=&day2=&month2=&
#       year2=&sunday=&monday=&tuesday=&wednesday=&thursday=&friday=&saturday="
#
