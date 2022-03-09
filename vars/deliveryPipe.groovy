def call(body){
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    
    pipeline {
      agent any
      stages{
          stage('TEST!'){
              steps {
                  writeFile file: "/main.tf", text: """
				  terraform {
  required_version = ">= 0.14"
  required_providers {
    aws = {
      version = "~> 3.52"
    }
  }
  backend "s3" {
    region = "eu-north-1"
    bucket = "eu-north-1-terraform"
    key    = "${INFRA_NAME}.tfstate"
  }
}
module "hashistack_cluster" {
  source = "${WORKSPACE}/mm-deploy/aws-terraform/_terraform"
  stack_name        = "${INFRA_NAME}"
  route_53_host     = "${DOMAIN}"
  volume_name       = "${DATABASE_VERSION}"
  volume_efs_mount  = "fsmt-0e1dff8c8a6870f08"
  app_name          = "mxn"
  client_instance = {
    memory = {
      ami   = "ami-00a139ddf01ce4fdd"
      price = "0.30"
      types = ["r5.2xlarge", "r5d.2xlarge", "r5n.2xlarge"]
      count = 1
      class = "back"
    }
    infra = {
      ami   = "ami-00a139ddf01ce4fdd"
      price = "0.30"
      types = ["r5.large", "r5d.large", "r5n.large"]
      count = 1
      class = "infra"
    }
  }
}
output "server_addr" {
  value = module.hashistack_cluster.server_addr
}
output "nomad_addr" {
  value = module.hashistack_cluster.nomad_addr
}
output "consul_addr" {
  value = module.hashistack_cluster.consul_addr
}
"""
              }
          }  
      }    
    }
}
