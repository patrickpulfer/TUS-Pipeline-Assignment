# Pipeline Assignment for TUS

Pipeline for Maven project that will build, test, analyse the code and deploy to the application to docker instance. Comprehensive code quality analysis and Java testing will be provided.

## Jenkins Pipeline
```
pipeline {
    agent any

    tools {
        jdk 'openjdk-17-jre'
        maven "Apache Maven 3.6.3"
    }

    stages {
        
        stage('Cleaning Workspace'){
            steps{
                sh 'echo Cleaning Workspace'
                cleanWs()
            }
        }
        
        stage('Fetch from GitHub'){
            steps{
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [],
                    submoduleCfg: [],
                    userRemoteConfigs: [[url: 'https://github.com/patrickpulfer/TUS-Pipeline-Assignment']]
                ])
            }
        }
        
        stage('Build') {
            steps {
                sh 'echo Building'
                sh 'mvn clean install'
            }
        }
            
        stage('Java Tests'){
            steps {
                sh 'echo Performing tests'
                sh 'mvn surefire-report:report'
                sh 'ls -la'
            }

            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.war'
                }
            }
        }
        
        stage('Code Analysis') {
            steps {
                sh "mvn checkstyle:check"
                recordIssues(tools: [checkStyle(reportEncoding: 'UTF-8')])
            }
            
            post{
                always{
                    recordIssues enabledForFailure: true, tools: [mavenConsole(), java(), javaDoc()]
                    //recordIssues enabledForFailure: true, tool: checkStyle()
                    recordIssues enabledForFailure: true, tool: spotBugs()
                    recordIssues enabledForFailure: true, tool: cpd(pattern: '**/target/cpd.xml')
                    recordIssues enabledForFailure: true, tool: pmdParser(pattern: '**/target/pmd.xml')
                }
            }
        }
        
        stage('Deploy to Docker') {
            steps {
                sh 'docker build -t car/app .'
            }
        }
    }
}
```