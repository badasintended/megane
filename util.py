import sys
from os.path import dirname, realpath


def get_script_dir():
    return dirname(realpath(sys.argv[0]))
