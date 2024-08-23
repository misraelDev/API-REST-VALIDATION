# API REST Validation
This project is an example of a REST API that implements validation in its endpoints. Here are some important guidelines:

- **Annotations**: Annotations are used in the DTOs (Data Transfer Objects) to ensure data integrity and guarantee that they comply with the established requirements before being processed.

- **HTTP status codes**: API endpoints handle HTTP responses according to the situation, returning status codes that accurately reflect the outcome of the operations (such as 200 OK, 400 Bad Request, 404 Not Found, etc.).

- **Versioning**: Endpoints are versioned to ensure compatibility with future versions of the API, allowing changes to be made without disrupting users who depend on previous versions.

Thanks for visiting! Don't forget to leave a star if you liked the project.

> [!WARNING]
> The project uses a PostgreSQL database, so you should change the credentials in the following path: `src/main/resources/application.properties`.

> [!IMPORTANT]
> In the root of the project, you will find the API documentation in the file named `REST-API VALIDATION.postman_collection.json`. There, you can test the different endpoints.
<p align="center">
  <img src="https://github.com/user-attachments/assets/56e58b72-f66b-432e-b58e-1da056548e7e" alt="Documentation in Postman">
</p>
