#!/usr/bin/env groovy

def call()
{
	pipeline {
    agent any
	
	options {
    skipDefaultCheckout(true)
	}
    stages {
	    
	    
	    echo "11111111111111111111111111111111"
	    
	    stage('Read YAML file') {
        steps {
		echo "222222222222222222222222222222222222"
            script{ datas = readYaml (file: 'env.yml') }
		echo "333333333333333333333333333333333333"
                  }
    }
	    
	    
	    
	    stage('Checkout') {
		    
		    echo "4444444444444444444444444444444444444444444"
			steps {
				scmFile(datas.branch,datas.gitUrl)
			}
		}
	    stage('Build') {
			steps {
				buildFile(datas.buildTool)
			}
		}
	    stage('Upload Artifacts') {
			steps {
				uploadArtifactory(datas.artifactoryTool)
			}
		}
	    stage('Download Artifacts') {
			steps {
				downloadArtifactory(datas.artifactoryTool)
			}
		}
	    stage('Publish Junit Reports') {
			steps {
				junitFile()
			}
		}

	}

}
}
