# Trabalho BackEnd

#Foi definido que a API terá serviços para atender os seguintes requisitos:

### 1. Ler o arquivo CSV das cidades para a base de dados:
 - GET Mapping;
 - Path: /v1/readCSV;
 - Expected Status: 200(OK) in the case where all is OK and there are cities to add, 404(NOT FOUND) 
 in the case where the .csv file was not found and 304(NOT MODIFIED) in the case where the .csv is empty; 
 - Expected Response Body: null.


### 2. Retornar somente as cidades que são capitais ordenadas por nome:
 - GET Mapping;
 - Path: /v1/capitals;
 - Expected Status: 200(OK); 
 - Expected Response Body: List containing all capitals found (27 items), ordered alphabetically (A to Z).
 
 
## 3. Retornar o nome do estado com a maior e menor quantidade de cidades e a quantidade de cidades:
 - GET Mapping;
 - Path: /v1/citiesCount;
 - Expected Status: 200(OK); 
 - Expected Response Body: City with the highest number of cities in the first line and the city with the 
 lowest number of cities in the second line, with their respective numbers of cities.
                         

## 4. Retornar a quantidade de cidades por estado:
 - GET Mapping;
 - Path: /v1/citiesByUF;
 - Expected Status: 200(OK); 
 - Expected Response Body: Each UF with their respective numbers of cities.

## 5. Obter os dados da cidade informando o id do IBGE;
 - GET Mapping;
 - Path: /v1/findById/{ibge_id};
 - Expected path variable: "ibge_id" with the city id to bem search.
 - Expected Status: 200(OK) in the case where the id exists and 404(NOT FOUND) otherwise; 
 - Expected Response Body: Each RS with their respective numbers of cities.
 
## 6. Retornar o nome das cidades baseado em um estado selecionado:
 - GET Mapping;
 - Path: /v1/findById/{uf};
 - Expected path variable: "uf" with the UF to be search;
 - Expected Status: 200(OK); 
 - Expected Response Body: List containing all cities found from the specified UF, not ordered.

## 7. Permitir adicionar uma nova Cidade:
 - POST Mapping;
 - Path: /v1/addCity;
 - Expected Request body: {
                                  "id": "",
                                  "uf": "",
                                  "name": "",
                                  "capital": ,
                                  "lon": 0.0
                                  "lat": 0.0,
                                  "no_accents": "",
                                  "alternative_names": "",
                                  "microregion": "",
                                  "mesoregion": ""
                             };
 - Expected Status: 201(Created); 
 - Expected Response Body: The information of the added city.

## 8. Permitir deletar uma cidade;
 - DELETE Mapping;
 - Path: /v1/deleteCity/{ibge_id};
 - Expected path variable: "uf" with the UF to be deleted;
 - Expected Status: 200(OK); 
 - Expected Response Body: The information of the deleted city.

## 9. Permitir selecionar uma coluna (do CSV) e através dela entrar com uma string para filtrar. retornar assim todos os objetos que contenham tal string:
 - GET Mapping;
 - Path: /v1/findByColumn/{column}/{search};
 - Expected path variables: "column" with the selected column from the csv file and "search" with the
 item to be searched;
 - Expected Status: 200(OK) in the case where the item was found and 404(NOT FOUND) otherwise; 
 - Expected Response Body: List containing all cities found from the specified search, not ordered.

## 10. Retornar a quantidade de registro baseado em uma coluna. Não deve contar itens iguais:
 - GET Mapping;
 - Path: /v1/itemsColumn/{column};
 - Expected path variables: "column" with the selected column from the csv file;
 - Expected Status: 200(OK);
 - Expected Response Body: Number of unique registers from the specified column.
 
## 10.1. Detalhar as informações do tópico anterior.
 - GET Mapping;
 - Path: /v1/itemsColumn/{column}/detail;
 - Expected path variables: "column" with the selected column from the csv file;
 - Expected Status: 200(OK);
 - Expected Response Body: List containing the unique registers from the specified column.   

## 11. Retornar a quantidade de registros total;
 - GET Mapping;
 - Path: /v1/registerCount;
 - Expected Status: 200(OK);
 - Expected Response Body: Number of registers in the database (5565 initially).

## 12. Dentre todas as cidades, obter as duas cidades mais distantes uma da outra com base na localização (distância em KM em linha reta):
 - GET Mapping;
 - Path: /v1/farthestCities;
 - Expected Status: 200(OK);
 - Expected Response Body: The information of the two farthest cities in the database.