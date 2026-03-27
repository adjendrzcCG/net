# Plan: Minimal Hosting Model Migration

## Strategy
**Flat sequential execution** — single project, no inter-project dependencies.
Tasks are ordered by concern to keep the repo always in a buildable state.

---

## Task List

| ID | Description | Status |
|----|-------------|--------|
| 01-program-cs | Rewrite Program.cs with minimal hosting (top-level statements, WebApplication.CreateBuilder) | ⏳ Pending |
| 02-remove-startup | Delete Startup.cs | ⏳ Pending |
| 03-modernize-codefiles | Apply file-scoped namespaces to HomeController.cs and ErrorViewModel.cs | ⏳ Pending |
| 04-enable-nullable | Enable Nullable + ImplicitUsings in csproj; fix nullable annotation on ErrorViewModel.RequestId | ⏳ Pending |
| 05-build-validate | Build the project; verify zero errors and zero warnings | ⏳ Pending |

---

## Execution Notes

- Tasks 01 and 02 must be done together logically (new Program.cs replaces Startup.cs)
- Tasks 03 and 04 are independent cosmetic/best-practice tasks
- Task 05 is the final validation gate

---

## Success Criteria

- [ ] `Program.cs` uses top-level statements and `WebApplication.CreateBuilder`
- [ ] `Startup.cs` no longer exists
- [ ] All C# files use file-scoped namespaces
- [ ] `<Nullable>enable</Nullable>` set in csproj
- [ ] `dotnet build` exits with code 0, zero errors, zero warnings
