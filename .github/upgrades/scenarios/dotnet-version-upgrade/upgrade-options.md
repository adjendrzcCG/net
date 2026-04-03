# Upgrade Options — SimpleWebApp

Assessment: 1 project (SimpleWebApp), net8.0 → net9.0, SDK-style, no external NuGet packages

## Strategy

### Upgrade Strategy
Single SDK-style project, no external package dependencies, straightforward TFM bump plus hosting model modernization.

| Value | Description |
|-------|-------------|
| **All-at-Once** (selected) | Upgrade the single project in one atomic operation — update TFM, migrate hosting model, validate build |
| Bottom-Up | N/A — single project, no dependency tiers |
| Top-Down | N/A — single project, no multi-project rollout |
