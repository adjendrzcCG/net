# Arc42 Documentation Setup

The Arc42 architecture documentation for SimpleWebApp has been generated.

## File Location

The generated documentation is at:
```
/home/runner/work/net/net/ARC42_DOCUMENTATION.md
```

## Move to Target Location

To place the documentation at the intended path (`docs/arc42/architecture.md`), run:

```bash
mkdir -p docs/arc42
mv ARC42_DOCUMENTATION.md docs/arc42/architecture.md
```

Or on Windows (PowerShell):
```powershell
New-Item -ItemType Directory -Force -Path "docs\arc42"
Move-Item ARC42_DOCUMENTATION.md "docs\arc42\architecture.md"
```

## Documentation Contents

The Arc42 document covers all 12 sections:

1. **Introduction and Goals** — Business context, quality goals, stakeholders
2. **Architecture Constraints** — Technical (net8.0, SDK, MVC), organizational, conventions
3. **System Scope and Context** — C4 Context diagram (Mermaid), technical interfaces
4. **Solution Strategy** — Technology decisions table, architecture decomposition diagram
5. **Building Block View** — Level 1/2/3 component + class diagrams, view hierarchy
6. **Runtime View** — Request lifecycle, error handling, static serving, startup sequences
7. **Deployment View** — Topology, infrastructure requirements, config resolution
8. **Cross-cutting Concepts** — Middleware pipeline, DI, logging, security, frontend
9. **Architecture Decisions** — 6 ADRs (Framework, Host, Routing, Error Handling, Bootstrap, DB)
10. **Quality Requirements** — Quality tree (mindmap), 10 quality scenarios, code metrics
11. **Risks and Technical Debt** — 9 risks (quadrant chart), 10 technical debt items
12. **Glossary** — 30+ domain and technical terms

## Embedded Diagrams (Mermaid)

| Section | Diagram Type | Description |
|---------|-------------|-------------|
| 3 | C4Context | System context showing user, browser, server, config, logging |
| 4 | graph TB | MVC architecture layers decomposition |
| 5.1 | graph TB | Level 1 high-level system decomposition |
| 5.2 | graph TB | Level 2 namespace/module structure |
| 5.3 | classDiagram | Detailed class structure with relationships |
| 5.4 | graph TB | Razor view component hierarchy |
| 6.1 | sequenceDiagram | Full page request lifecycle (GET /Home/About) |
| 6.2 | flowchart TD | Error handling flow (Dev vs Production) |
| 6.3 | sequenceDiagram | Static asset serving flow |
| 6.4 | sequenceDiagram | Application startup sequence |
| 7.2 | graph TB | Deployment topology (client → server → infra) |
| 7.4 | flowchart LR | Configuration resolution order |
| 8.1 | flowchart TD | Middleware pipeline architecture |
| 8.3 | graph LR | Logging strategy |
| 10.1 | mindmap | Quality tree |
| 11.1 | quadrantChart | Risk matrix (likelihood vs. impact) |
