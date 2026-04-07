# 01-modernize-project-file: Enable Modern csproj Properties

## Objective
Add `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>` to `SimpleWebApp.csproj`.

## Affected Files
- `/home/runner/work/net/net/SimpleWebApp/SimpleWebApp.csproj`

## Current State
```xml
<Project Sdk="Microsoft.NET.Sdk.Web">
  <PropertyGroup>
    <TargetFramework>net8.0</TargetFramework>
    <RootNamespace>SimpleWebApp</RootNamespace>
    <AssemblyName>SimpleWebApp</AssemblyName>
  </PropertyGroup>
</Project>
```

## Changes Required
1. Add `<Nullable>enable</Nullable>` — enables nullable reference type annotations and warnings
2. Add `<ImplicitUsings>enable</ImplicitUsings>` — enables global using directives for common .NET namespaces

## Done When
- `SimpleWebApp.csproj` contains both properties
- Project builds (nullable warnings will be fixed in task 03)
