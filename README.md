# BACKEND

## PASOS PARA REALIZAR DESPLIEGUE  DEL BACKEND EN GOOGLE CLOUD RUN

La guía a seguir se encuentra en el link:<br>

<https://cloud.google.com/run/docs/quickstarts/build-and-deploy/deploy-java-service>

1. inicializar el Google Cloud CLI:<br>

   >  *`gcloud init`*


2. Luego configurar el ID del Proyecto en el que se desplegará la aplicación:<br>

   >  *`gcloud config set project PROJECT_ID`*


3. Luego se habilitan Cloud Run Admin API y Cloud Build API:<br>

   >  *`gcloud services enable run.googleapis.com cloudbuild.googleapis.com`*

4. Luego se crean los roles para permitir a Cloud Build crear y desplegar aplicaciones desde el código fuente:<br>

   >  *`gcloud projects add-iam-policy-binding PROJECT_ID \
   >      --member=serviceAccount:PROJECT_NUMBER-compute@developer.gserviceaccount.com \
   >      --role=roles/cloudbuild.builds.builder`*

5. Finalmente para desplegar la aplicación spring boot, <br>
nos ubicamos en el directorio del código fuente y ejecutamos el comando:<br>

   >  *`gcloud run deploy --source .`*

   - :memo: ***Nota: La aplicación spring boot, debe tener configurado el puerto 8080 para el servidor integrado***

   ![Deploy to Cloud Run](https://raw.githubusercontent.com/kimmi1206/documentacion-bancodebogota/refs/heads/main/DESPLIEGUE/deploy-gcloud-run.png)



## REPOSITORIOS:

#### - FRONTEND: https://github.com/kimmi1206/front-bancodebogota

#### - BACKEND:	 https://github.com/kimmi1206/back-bancodebogota



## ENLACES DESPLEGADOS:

#### - FRONTEND: http://kimm-prueba-bancobogota91.s3-website.us-east-2.amazonaws.com/

#### - BACKEND:	 https://back-bancodebogota-608870366046.us-central1.run.app/



## API ENDPOINTS:

> - **GET Cliente:**
>     - Url:  *`/api/v1/clientes/buscar`*
>     - Query Parameters: *tipoDocumento: String*
>                         *numeroDocumento: String*
>     - Example: <https://back-bancodebogota-608870366046.us-central1.run.app/api/v1/clientes/buscar?tipoDocumento=C&numeroDocumento=23445322>
>
>
> - **GET Archivo Clientes:**
>     - Url:  *`/api/v1/clientes/download`*
>     - Example: <https://back-bancodebogota-608870366046.us-central1.run.app/api/v1/clientes/download>
>
>
> - **SWAGGER API Docs:**
>     - Url:  *`/swagger-ui/index.html`*
>     - Example: <https://back-bancodebogota-608870366046.us-central1.run.app/swagger-ui/index.html>

