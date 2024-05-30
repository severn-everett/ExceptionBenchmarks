import argparse
import csv
import math

import matplotlib.pyplot as plt
from matplotlib.ticker import StrMethodFormatter


class ExceptionRun:
    def __init__(self, title):
        self.title = title
        self.results = []
        self.error_ranges = []


exception_runs: list[ExceptionRun] = []
ppm = []


def parse_headers(row):
    cell_count = 1
    for cell in row:
        if cell_count != 1 and cell_count % 2 == 0:
            exception_runs.append(ExceptionRun(cell))
        cell_count += 1


def parse_data(row):
    cell_count = 1
    for cell in row:
        if cell_count == 1:
            ppm.append(int(cell))
        else:
            exception_run = exception_runs[math.floor(cell_count / 2) - 1]
            value = float(cell)
            if cell_count % 2 == 0:
                exception_run.results.append(value)
            else:
                exception_run.error_ranges.append(value)

        cell_count += 1


def parse_file(file_name):
    with open(file_name) as csv_file:
        line_count = 1
        line_reader = csv.reader(csv_file)
        for row in line_reader:
            if line_count == 1:
                parse_headers(row)
            else:
                parse_data(row)

            line_count += 1


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Create a bar chart from results.')
    parser.add_argument('-f', '--file', type=str, help='the data file')
    # parser.add_argument('-y', '--y_label', type=str, help='the y-axis label')
    args = parser.parse_args()

    parse_file(args.file)

    for er in exception_runs:
        plt.errorbar(ppm, er.results, label=er.title, yerr=er.error_ranges)

    plt.xscale('log')
    plt.yscale('log')
    major_formatter = StrMethodFormatter('{x:.0f}')
    plt.gca().get_yaxis().set_major_formatter(major_formatter)
    plt.gca().get_xaxis().set_major_formatter(major_formatter)
    label_size = 'x-large'
    plt.xlabel('PPM', fontsize=label_size, fontweight='bold')
    plt.ylabel('Execution Time (ns)', fontsize=label_size, fontweight='bold')
    # plt.ylabel(args.y_label, fontsize=label_size, fontweight='bold')
    plt.grid(visible=True, color='0.8')
    plt.legend()
    plt.tight_layout()

    plt.show()
