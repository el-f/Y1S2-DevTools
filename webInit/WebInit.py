#!/usr/bin/python
import subprocess

from flask import Flask, request

app = Flask("__name__")


@app.route("/")
def airport():
    return subprocess.check_output(["java", "-classpath", "C:/Users/Elazar/Documents/GitHub/Y1S2-Homework/bin",
                                    "AirportProject.Program",
                                    request.args.get('d'),      # direction
                                    request.args.get('cnt'),    # country
                                    request.args.get('cty'),    # city
                                    request.args.get('ap'),     # airport
                                    request.args.get('trm'),    # terminal
                                    request.args.get('wd'),     # dayofweek
                                    request.args.get('start'),  # date range lower bound
                                    request.args.get('end'),    # date range higher bound
                                    request.args.get('cmp')     # company
                                    ])


if __name__ == "__main__":
    app.run(port=8000, host="0.0.0.0", debug=True)

# http://localhost:8000/?d=departures&cnt=&cty=&ap=&trm=&wd=&start=&end=2020/11/21-22:22:22&cmp=
# http://localhost:8000/?d=&cnt=&cty=&ap=&trm=&wd=&start=&end=&cmp=
