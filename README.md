# Migration Document Sharing Tool
___

## Prerequisites for using this project
___

* JAVA 8 installed.
* Access to WSO2 migration document repository.

## Steps to use the tool
___

1. Clone the repository and build the JAR.
2. Generate a GitHub personal access token.
3. Copy the built `MigrationDocumentCreator-1.0-SNAPSHOT-jar-with-dependencies.jar`, `config.yaml`, and `migration-docs-artifacts.json` to a single directory.
4. Open `config.yaml` and update the GitHub username, token, repository, directory, organization, product, source, target, dependency, and attachment prefixes with the correct values.

   ```yaml
   Sample configuration
   git:
     enable: false
     username: <username>
     token: <token>
     repository: https://github.com/wso2-enterprise/migration-docs
     directory: migration-docs
     organization: wso2-enterprise
   doc:
     product: APIM
     source: 3.1.0
     target: 4.1.0
     dependency: isaskm
     attachmentPrefixes: ['wso2is-migration-', 'wso2am-migration-', 'wso2is-extensions-', 'org.wso2.carbon.apimgt.migrate.client-']
    ```yaml
5. Execute the following command:
````
java -jar MigrationDocumentCreator-1.0-SNAPSHOT-jar-with-dependencies.jar
````