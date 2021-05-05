#!/usr/bin/python
import os
import secrets
import subprocess

from flask import Flask, request, render_template, flash, url_for
from forms import FlightsForm

app = Flask(__name__)

app.secret_key = secrets.token_hex(16)

command = [
    "java", "-classpath",
    os.getcwd() + "/../target/classes",  # get parent of working dir then go to classes folder
    "AirportProject.Program",  # Package.Class_File
]


def get_response_for_args(args):  # decode() - decode bytes to string
    return render_template(
        "results.html",
        title="Filtered Results",
        flights=subprocess.check_output(command + ["HTML"] + args).decode().split("<br>")
    )


@app.route("/", methods=['GET', 'POST'])
def submit():
    form = FlightsForm()
    if form.validate_on_submit():
        flash(u'Filters Processed!', 'success')
        return get_response_for_args(
            [
                form.direction.data,
                form.country.data,
                form.city.data,
                form.airport.data,
                form.airline.data,
                str(form.date1.data.day),
                str(form.date1.data.month),
                str(form.date1.data.year),
                str(form.date2.data.day),
                str(form.date2.data.month),
                str(form.date2.data.year),
                str(form.sunday.data).lower(),
                str(form.monday.data).lower(),
                str(form.tuesday.data).lower(),
                str(form.wednesday.data).lower(),
                str(form.thursday.data).lower(),
                str(form.friday.data).lower(),
                str(form.saturday.data).lower(),
                "" if form.terminal.data is None else str(form.terminal.data)
            ]
        )
    return render_template('form.html', title='Submit Filters', form=form)


@app.route("/about")
def about():
    return render_template("about.html", title='About')


def get_url(direction):
    return f"<a href={direction.lower()}?outformat=html&country=&city=&airport=&airline=&day1" \
           f"=&month1=&year1=&day2=&month2=&year2=&sunday=&monday=&tuesday=&wednesday=&thursday=&friday=&saturday=" \
           f"&terminal=>{direction}</a><br>"


def go_back_url(step="", outformat=""):
    return "" if outformat == "text" else f"<a href=/{step}>Go Back</a><br><br>"


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
        command + [outformat, direction] + list(request.args.values())[1:]  # [1:] - remove first element
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
