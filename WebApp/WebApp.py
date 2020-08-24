#!/usr/bin/python
import os
import secrets
import subprocess

from flask import Flask, request, render_template, flash, url_for
from forms import FlightsForm

app = Flask(__name__)

app.config['SECRET_KEY'] = secrets.token_hex(16)

path = os.getcwd() + "/../target/classes"  # get parent of working dir then go to classes folder

command = [
    "java", "-classpath", path,
    "AirportProject.Program",  # Package.Class_File
]


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
            command + [
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
            sunday=str(form.sunday.data).lower(),
            monday=str(form.monday.data).lower(),
            tuesday=str(form.tuesday.data).lower(),
            wednesday=str(form.wednesday.data).lower(),
            thursday=str(form.thursday.data).lower(),
            friday=str(form.friday.data).lower(),
            saturday=str(form.saturday.data).lower()
        )
    return render_template('form.html', title='Submit Filters', form=form)


def get_url(direction):
    return "<a href=http://localhost:8000/" + direction.lower() + \
           "?outformat=html&country=&city=&airport=&airline=&day1=&month1=&year1=" \
           "&day2=&month2=&year2=&sunday=&monday=&tuesday=&wednesday=&thursday=&friday=&saturday=>" \
           + direction + "</a><br>"


def go_back_url(step="", outformat=""):
    return "" if outformat == "text" else "<a href=http://localhost:8000/" + step + ">Go Back</a><br><br>"


@app.route("/legacy")
def legacy():
    return go_back_url() + "Example links:<br>" + get_url("Arrivals") + get_url("Departures") + get_url("All")


@app.route("/departures")
def departures():
    return all_flights("departures")


@app.route("/arrivals")
def arrivals():
    return all_flights("arrivals")


@app.route("/all")
def all_flights(direction="all"):
    outformat = request.args.get('outformat')
    return go_back_url("legacy", outformat).encode() + subprocess.check_output(
        command
        + [
            outformat,
            direction
        ] + list(request.args.values())[1:]  # [1:] - remove first element
    )


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
