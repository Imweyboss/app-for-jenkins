def call(text){
    def pipelineParams = [:]
    text.resolveStrategy = Closure.DELEGATE_FIRST
    text.delegate = pipelineParams
    text()
  pipeline {
      agent any
    stage('TEST!'){
      steps {
        echo 'TSSSSSSSSSSSS'
      }
    }  
  }
}
