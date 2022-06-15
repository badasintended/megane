#!/usr/bin/bash

(
    cd "module-compat"

    for MODULE in */; do
        (
            cd "$MODULE"
            if [ -e "setup.sh" ]; then
                echo "Found setup script for module ${MODULE::-1}, running that"
                bash "setup.sh"
            fi
        )
    done

    echo "Done."
)
