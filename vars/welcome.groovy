def call(Map pipelineParams){
    pipeline {
        agent any
        stage('TEST!'){
            steps {
                echo 'TSSSSSSSSSSSS'
            }
        }  
    }
}
