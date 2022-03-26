from random import randrange
from shutil import copyfileobj, copytree, rmtree, make_archive
from os.path import dirname
from os import makedirs, mkdir, rename
import json
from inspect import cleandoc
from urllib.request import Request, urlopen
import properties as prop
from util import get_script_dir


mod_metadata = {
    "schemaVersion": 1,
    "id": "megane",
    "version": prop.version,
    "name": "megane",
    "description": "wthit plugin that shows more tooltip than you'll ever want",
    "authors": [
        "deirn"
    ],
    "contributors": [
        "Lyaiya",
        "qsefthuopq"
    ],
    "contact": {
        "homepage": "https://github.com/badasintended/megane",
        "sources": "https://github.com/badasintended/megane",
        "issues": "https://github.com/badasintended/megane/issues"
    },
    "license": "MIT",
    "icon": "assets/megane/icon.png",
    "environment": "*",
    "entrypoints": {
        "modmenu": [
            "lol.bai.megane.runtime.config.MeganeModMenu"
        ]
    },
    "depends": prop.dependencies,
    "jars": []
}

script_dir = get_script_dir()
input_dir = f"{script_dir}/input"
output_dir = f"{script_dir}/output"

rmtree(input_dir, ignore_errors=True)
rmtree(output_dir, ignore_errors=True)

mkdir(input_dir)
mkdir(output_dir)


def get_module_url(name: str, version: str):
    return f"https://maven.bai.lol/lol/bai/megane/megane-{name}/{version}/megane-{name}-{version}.jar"


def download_file(url: str, file_path: str):
    req = Request(url)
    req.add_header("User-Agent", f"Wget/1.0.{randrange(1, 100)}")

    with urlopen(req) as res:
        makedirs(dirname(file_path), exist_ok=True)

        with open(file_path, "wb") as file:
            copyfileobj(res, file)


for name, version in prop.modules.items():
    print(f"Downloading {name} v{version}")
    jar_name = f"megane-{name}-{version}.jar"
    jar_path = f"META-INF/jars/{jar_name}"
    url = get_module_url(name, version)

    download_file(url, f"{input_dir}/{jar_path}")

    mod_metadata["jars"].append({
        "file": jar_path
    })

print("Creating fabric.mod.json")
with open(f"{input_dir}/fabric.mod.json", "w") as file:
    json.dump(mod_metadata, file)

print("Creating manifest")
with open(f"{input_dir}/META-INF/MANIFEST.MF", "w") as file:
    file.write(cleandoc("""
    Manifest-Version: 1.0
    Created-By: 69 (megane-bundler)
    """))

print("Copying static files")
copytree(f"{script_dir}/static", f"{input_dir}", dirs_exist_ok=True)

print("Creating bundle jar")
jar_path = f"{output_dir}/megane-{prop.version}.jar"
rename(make_archive(f"{output_dir}/zip", "zip", input_dir), jar_path)

print(f"Done, output at \"{jar_path}\"")
