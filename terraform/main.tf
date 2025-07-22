terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"
    }
  }
}

provider "docker" {
  host = "unix:///var/run/docker.sock"
}

resource "docker_image" "postgres" {
  name = "postgres:15"
}

resource "docker_container" "postgres" {
  name  = "pg-test"
  image = docker_image.postgres.name

  ports {
    internal = 5432
    external = 15432
  }

  env = [
    "POSTGRES_PASSWORD=123456",
    "POSTGRES_DB=cloudbanking"
  ]
}
