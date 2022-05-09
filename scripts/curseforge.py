import json
from os import environ
from urllib.request import Request, urlopen
from urllib3 import encode_multipart_formdata
import properties as prop
from properties import CurseForge
from util import get_base_dir


script_dir = get_base_dir()

api_token = environ["CURSEFORGE_API"]
api_base_url = "https://minecraft.curseforge.com/api"


def create_request(path: str):
    req = Request(f"{api_base_url}/{path}")
    req.add_header("X-Api-Token", api_token)
    return req


def get_json(path: str):
    any = None
    req = create_request(path)
    with urlopen(req) as res:
        any = json.load(res)
    assert any != None
    return any


valid_version_types: list = get_json("game/version-types")
valid_game_versions: list = get_json("game/versions")


def get_version_id(version: str):
    for valid_version in valid_game_versions:
        if version == valid_version["name"]:
            for valid_type in valid_version_types:
                type_slug = str(valid_type["slug"])
                if type_slug.startswith("minecraft") or type_slug in ["modloader", "java"]: 
                    if valid_version["gameVersionTypeID"] == valid_type["id"]:
                        return int(valid_version["id"])

    return None


game_versions = []
for version in CurseForge.game_versions:
    version_id = get_version_id(version)
    if version_id != None:
        game_versions.append(version_id)


def create_relations(type: str, slugs: 'list[str]'):
    res = []
    for slug in slugs:
        res.append({
            "slug": slug,
            "type": type
        })
    return res


req = create_request(f"projects/{CurseForge.project_id}/upload-file")
req.method = "POST"
file_name = f"megane-{prop.version}.jar"
data, content_type = encode_multipart_formdata({
    "file": (file_name, open(f"{script_dir}/output/{file_name}", "rb").read()),
    "metadata": json.dumps({
        "changelog": f"https://github.com/badasintended/megane/releases/tag/{prop.version}",
        "changelogType": "markdown",
        "displayName": f"[{prop.minecraft_version}] v{prop.version}",
        "gameVersions": game_versions,
        "releaseType": CurseForge.release_type,
        "relations": {
            "projects": [
                *create_relations("requiredDependency",
                                  CurseForge.required_dependencies),
                *create_relations("optionalDependency",
                                  CurseForge.optional_dependencies)
            ]
        }
    })
})
req.add_header("Content-Type", content_type)
req.data = data

with urlopen(req) as res:
    id = json.load(res)["id"]
    print(
        f"Done, uploaded to \"https://www.curseforge.com/minecraft/mc-mods/megane/files/{id}\"")
