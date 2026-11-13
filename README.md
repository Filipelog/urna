# Urna Eletrônica Swing

Aplicativo desktop Java 25 LTS com interface Java Swing, Maven e integração com Firebase Cloud Firestore. A simulação contém dois cargos fictícios: Deputado Federal (proporcional) e Governador (majoritário).

## Executar

1. Instale JDK 25 LTS e Maven.
2. Opcionalmente configure o Firestore (veja abaixo).
3. Execute `mvn clean compile exec:java`.

Sem credenciais, o aplicativo funciona em modo local para demonstração e informa no console que os dados não serão persistidos na nuvem.

## Configuração do Firebase

1. Crie um projeto no [Firebase Console](https://console.firebase.google.com/) e habilite o Cloud Firestore.
2. Em **Configurações do projeto > Contas de serviço**, gere uma chave privada.
3. Não coloque o JSON no Git. Defina `GOOGLE_APPLICATION_CREDENTIALS` apontando para o arquivo:
   - PowerShell: `$env:GOOGLE_APPLICATION_CREDENTIALS="C:\seguro\firebase-service-account.json"`
4. Execute o aplicativo novamente. A coleção `candidatos` será consultada e a coleção `simulacoes` receberá os votos anônimos e os agregados.

Se `candidatos` estiver vazia, candidatos fictícios de demonstração são usados para que o fluxo continue funcional.

## Estrutura

- `model`: entidades, cargos e votos.
- `view`: telas Swing.
- `controller`: navegação e controle do fluxo.
- `service`: regras de votação.
- `repository`: contratos e Firestore.
- `config`: inicialização segura do Firebase.
