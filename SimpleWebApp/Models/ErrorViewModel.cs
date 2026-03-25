namespace SimpleWebApp.Models;

public class ErrorViewModel
{
    // Nullable annotation: RequestId may legitimately be absent before assignment.
    public string? RequestId { get; set; }

    public bool ShowRequestId => !string.IsNullOrEmpty(RequestId);
}

