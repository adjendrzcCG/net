# Upgrade Options — SimpleWebApp

Assessment: 1 project (SimpleWebApp), already net8.0, code pattern modernization only

## Strategy

### Upgrade Strategy
Single project already targeting net8.0 — only code pattern modernization needed (minimal hosting, nullable, file-scoped namespaces).

| Value | Description |
|-------|-------------|
| **All-At-Once** (selected) | All changes applied simultaneously in a single atomic operation |
| Bottom-Up | Upgrade leaf dependencies first — not needed for single project |
| Top-Down | Upgrade applications first — not needed for single project |

## Modernization

### Nullable Reference Types
Project targets net8.0 but `<Nullable>enable</Nullable>` is not set in the csproj.

| Value | Description |
|-------|-------------|
| **Enable** (selected) | Add `<Nullable>enable</Nullable>` to csproj and fix all resulting nullable warnings |
| Warnings Only | Enable nullable as warnings only |
| Skip | Do not enable nullable reference types |
