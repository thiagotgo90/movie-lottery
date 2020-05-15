import * as cdk from '@aws-cdk/core';
import * as lambda from '@aws-cdk/aws-lambda';
import * as apigw from '@aws-cdk/aws-apigateway';

import { HitCounter } from './hitcounter';
import { TableViewer } from 'cdk-dynamo-table-viewer';


export class CdkStack extends cdk.Stack {
  constructor(scope: cdk.App, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    
    const hello = new lambda.Function(this, 'Hello', {
      runtime: lambda.Runtime.NODEJS_10_X,
      code: lambda.Code.fromAsset("lambda"),
      handler: "hello.handler"
    });
    
    const helloWithCounter = new HitCounter(this, "helloWithCounter", {
      downstream: hello
    })
    new apigw.LambdaRestApi(this, "Endpoint", {
      handler: helloWithCounter.handler
    })

    new TableViewer(this, "TableViewer", {
      table: helloWithCounter.table,
      title: 'Hello Hits',
      sortBy: '-hits'
    })
  }
}
