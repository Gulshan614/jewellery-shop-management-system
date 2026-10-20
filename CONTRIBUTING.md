# Contributing

## Workflow

1. Pull the latest `main`: `git checkout main && git pull`
2. Create a branch for your work: `git checkout -b feature/<module>-<short-description>`
   (examples: `feature/product-crud`, `fix/billing-tax-rounding`)
3. Make small, focused commits with clear messages (`Add product search endpoint`).
4. Run `mvn test` before pushing.
5. Push your branch and open a Pull Request into `main`.
6. Get at least one review and a passing CI check before merging.

## Rules

- Do not commit directly to `main`.
- Do not commit secrets, `.env` files, or IDE files (`.classpath`, `.project`, `.idea/`).
- Follow the package layout: `controller`, `service`/`service.impl`, `repository`, `entity`, `dto`, `exception`, `config`, `event`.
- Controllers use DTOs, never entities. Validate input with Bean Validation and let the central exception handler build error responses.
- Add unit tests (JUnit + Mockito) for new service logic.
