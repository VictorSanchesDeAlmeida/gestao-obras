# Projeto API para gestão de obra

## Descrição do projeto

Esta API foi desenvolvida para facilitar a gestão de obras, oferecendo funcionalidades como controle de projetos,
acompanhamento de tarefas, gestão de materiais e monitoramento de prazos. Com uma arquitetura eficiente e segura, a API
permite integração com sistemas externos e fornece endpoints otimizados para um fluxo de trabalho ágil e organizado.

## Autenticação - Rota /login

A rota ``/login`` é responsável por autenticar usuários e gerar um token JWT para acesso seguro ao sistema.

### **Requisição**

* **Endpoint:** `POST /login`
* **Headers:** `Content-Type: application/json`
* **Corpo da Requisição:**

```JSON
{
"username": "admin",
"password": "123"
}
```

### Resposta

* **Código de Status:** 200 OK (em caso de sucesso)
* **Corpo da Resposta:**

```JSON
{
"accessToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJnZXN0YW9fb2JyYV9hcGkiLCJzdWIiOiJiMTg3NDFkNC1jYzU1LTQ3NDUtYmI4Ni1kZDllZDQ2YzA2MTkiLCJleHAiOjE3NDIyOTY4NjYsImlhdCI6MTc0MjI5NjU2Nn0.IeeaQlmQ8QIVYYFH1-HBklHdjkSu13flHZIQklf2bCIlGqy0qCu-6RxgU3JYxOvvX01_TmlcDsvcnSi30j0a0vL3_YgQb4Viqa9G0AGqnL4uzrPAhpzAqrXDxPmYQk2Ve06JHsX5fRGezWoTntDrvayBoJ8VCKelD8Mil4jDwOaVEgM-1oTygYg0lSUWTs1HBSX1NkAE_qKVQ9PybPm80ZDe96lLlE3u0omQ_AyMUHll4OHqEEisqMZaLHwbKh_7A-y2DqISk_SK8XVDExAH263EjkbPWak2yGcECAEn6JXu6Tzjl7JFIoreUU9FfVWTsWyecA0zdOfJFK2w2FQeUA",
"expiresIn": 300
}
```

### Explicação dos Campos

* `accessToken:` Token JWT gerado para autenticação do usuário.
* `expiresIn:` Tempo de expiração do token em segundos (neste caso, 300 segundos = 5 minutos).
* **O token é assinado e emitido pela aplicação** ``gestao_obra_api``.

### Possíveis Erros

| Código |                   Mensagem                    |
|:------:|:---------------------------------------------:|
|  401   | ``{"error": "User or password is invalid!"}`` |
