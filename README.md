# Stoom - LUAN LUNA

Após executar a aplicação, é possível acessar a documentação (Swagger) através do link **[HOST]/stoom/v1/swagger-ui.html**


Teste de Qualificação Backend STOOM
===================

O teste consiste em criar no padrão REST, um CRUD (Create, Read, Update, Delete) de uma entidade endereço com os seguintes atributos:

- id*
- streetName*
- number*
- complement
- neighbourhood*
- city*
- state*
- country*
- zipcode*
- latitude
- longitude

**Obs.:** Os atributos marcados com * devem ser obrigatórios



**Obrigatório**

[**DONE**] 1- Deve-se utilizar Java para criação desse CRUD. O framework pode ser o que se sentir mais à vontade.
- **Spring + Java8**

[**DONE**] 2- Deve-se criar um repositório público no github para compartilhar o teste e este ser enviado ao examinador na conclusão
- **https://github.com/LuanLuna/stoom**


**Diferenciais**

[**DONE**] 1- Quando latitude e longitude não forem informados, o sistema precisa buscar essa informação utilizando a Geocoding API do Google (https://developers.google.com/maps/documentation/geocoding/start)

- Use a chave temporária enviada por e-mail

[**DONE**] 2- Criar um Dockerfile funcional com o projeto

- Obs.: Não precisa se preocupar com banco de dados no Dockerfile, ele será executado usando em nosso ambiente que já irá considerar isso

[**DONE**] 3- Criar ao menos um teste unitário básico para cada ação (Create, Read, Update, Delete)
