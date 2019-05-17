#!/usr/bin/env groovy

def call()
{
	
pipeline {
    agent any
	
	options {
    skipDefaultCheckout(true)
		}
    stages {
    	    stage('Checkout') {    
			steps {
				script{ url = sh(returnStdout: true, script: 'git config remote.origin.url').trim() }
				script{ branchName = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref').trim() }
				echo "11111111111111111 "

				echo "22222222222222222 ${branchName}"
				echo "33333333333333333 "
				scmFile("master", url)
			}
		}
	    stage('Read YAML file') {
        		steps {
            			script{ datas = readYaml (file: 'env.yml') }
                  	}
    		}
	    stage('Build') {
			steps {
				buildFile(datas)
			}
		}
	    stage('Upload Artifacts') {
			steps {
				uploadArtifactory(datas)
			}
		}
	    stage('Download Artifacts') {
			steps {
				downloadArtifactory(datas)
			}
		}
	    stage('Publish Junit Reports') {
			steps {
				junitFile(datas)
			}
		}

	}

}
}
