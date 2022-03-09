def call(Map pipelineParams){
    pipeline {
        agent any
        stages('TEST!'){
            steps {
                echo 'TSSSSSSSSSSSS'
            }
        }  
    }
}
