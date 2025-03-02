#!/bin/sh

git switch main
git pull
docker compose up --build -d
