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


def display_results(flights=None, template="results.html"):
    if flights is None:
        flights = []
    return render_template(template_name_or_list=template, title="Filtered Results", flights=flights)


def get_response_for_args(
        direction="all",
        country="",
        city="",
        airport="",
        airline="",
        day1="",
        month1="",
        year1="",
        day2="",
        month2="",
        year2="",
        sunday="",
        monday="",
        tuesday="",
        wednesday="",
        thursday="",
        friday="",
        saturday=""
):
    return display_results(
        flights=str(subprocess.check_output(
            ["java", "-classpath", path,
             "AirportProject.Program",  # Package.Class_File
             "HTML",
             direction,
             country,
             city,
             airport,
             airline,
             day1,
             month1,
             year1,
             day2,
             month2,
             year2,
             sunday,
             monday,
             tuesday,
             wednesday,
             thursday,
             friday,
             saturday
             # terminal
             ])
        )[2:][:-1].split("<br>")  # [2:] - remove first two chars. [:-1] - remove last char
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


@app.route("/legacy")
def legacy():
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
                                    request.args.get('sunday'),
                                    request.args.get('monday'),
                                    request.args.get('tuesday'),
                                    request.args.get('wednesday'),
                                    request.args.get('thursday'),
                                    request.args.get('friday'),
                                    request.args.get('saturday')
                                    # ,request.args.get('terminal'),  # terminal
                                    ])


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