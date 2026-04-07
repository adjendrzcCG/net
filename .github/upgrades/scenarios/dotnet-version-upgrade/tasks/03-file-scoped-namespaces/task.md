# 03-file-scoped-namespaces: Apply File-Scoped Namespaces and Fix Nullable Warnings

## Objective
1. Convert block-scoped namespace declarations to file-scoped (`namespace X;`) in all C# files
2. Fix the CS8618 nullable warning: change `ErrorViewModel.RequestId` from `string` to `string?`
3. Remove `using` directives now covered by implicit usings

## Files to Modify

### Controllers/HomeController.cs
- Convert namespace to file-scoped
- Remove `using System.Diagnostics;` — covered by implicit usings
- Remove `using Microsoft.AspNetCore.Mvc;` — covered by implicit usings for ASP.NET Core
- Remove `using Microsoft.Extensions.Logging;` — covered by implicit usings
- Remove `using SimpleWebApp.Models;` — keep (project-specific, not implicit)

Note: After enabling ImplicitUsings, the SDK-default global usings for `Microsoft.NET.Sdk.Web` include:
`System`, `System.Collections.Generic`, `System.IO`, `System.Linq`, `System.Net.Http`,
`System.Net.Http.Json`, `System.Threading`, `System.Threading.Tasks`,
`Microsoft.AspNetCore.Builder`, `Microsoft.AspNetCore.Hosting`, `Microsoft.AspNetCore.Http`,
`Microsoft.AspNetCore.Routing`, `Microsoft.Extensions.Configuration`, `Microsoft.Extensions.DependencyInjection`,
`Microsoft.Extensions.Hosting`, `Microsoft.Extensions.Logging`

`System.Diagnostics`, `Microsoft.AspNetCore.Mvc`, and `Microsoft.Extensions.Logging` ARE covered.
`SimpleWebApp.Models` is NOT covered — keep it.

### Models/ErrorViewModel.cs
- Convert namespace to file-scoped
- Change `public string RequestId { get; set; }` → `public string? RequestId { get; set; }` (fixes CS8618)

## Done When
- Both files use file-scoped namespaces
- `ErrorViewModel.RequestId` is `string?`
- `dotnet build` shows 0 errors and 0 warnings
