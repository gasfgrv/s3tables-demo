#!/bin/bash

echo "Criando bucket para tabelas..."
awslocal s3tables create-table-bucket --name meu-bucket-de-tabelas

echo "Criando namespace..."
awslocal s3tables create-namespace \
    --table-bucket-arn arn:aws:s3tables:us-east-1:000000000000:bucket/meu-bucket-de-tabelas \
    --namespace meu_projeto

echo "Criando tabela..."
awslocal s3tables create-table \
    --table-bucket-arn arn:aws:s3tables:us-east-1:000000000000:bucket/meu-bucket-de-tabelas \
    --namespace meu_projeto \
    --name minha_tabela \
    --format ICEBERG
