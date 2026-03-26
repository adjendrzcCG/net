# Upgrade Plan: SimpleWebApp .NET Modernization

**Created**: 2026-03-26  
**Strategy**: All-At-Once  
**Target Framework**: net8.0 (already correct; modernizing code patterns)

---

### Selected Strategy
**All-At-Once** — Single project, all changes applied in a single atomic operation.  
**Rationale**: 1 project, already on net8.0, straightforward pattern modernization — no breaking changes expected.

---

## Task List

### Task 01 — Migrate to Minimal Hosting API

**Scope**: Consolidate `Program.cs` + `Startup.cs` into a single `Program.cs` using the minimal hosting model.

**Files**:
- Rewrite: `SimpleWebApp/Program.cs`
- Delete: `SimpleWebApp/Startup.cs`
- Update: `SimpleWebApp/Controllers/HomeController.cs` (file-scoped namespace)

**Changes**:
1. Replace `CreateHostBuilder` + `UseStartup<Startup>()` with `WebApplication.CreateBuilder(args)`
2. Move service registrations (`AddControllersWithViews`) into `Program.cs`
3. Move middleware pipeline (`UseHttpsRedirection`, `UseStaticFiles`, `UseRouting`, `UseAuthorization`, `UseEndpoints`) into `Program.cs` using simplified form
4. Use top-level statements (no `Program` class wrapper)
5. Update `HomeController.cs` and `ErrorViewModel.cs` to use file-scoped namespace declarations

**Validation**: Project builds with 0 errors and 0 warnings.

---

## Success Criteria

- [ ] `Startup.cs` deleted
- [ ] `Program.cs` uses minimal hosting API with top-level statements
- [ ] All service registrations and middleware in `Program.cs`
- [ ] All C# files use file-scoped namespaces
- [ ] Build succeeds with 0 errors, 0 warnings
