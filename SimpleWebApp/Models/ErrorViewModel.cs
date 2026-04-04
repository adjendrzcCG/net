namespace SimpleWebApp.Models;

public class ErrorViewModel
{
    // Nullable: RequestId is set after construction via object initializer and
    // can legitimately be null, so string? is the correct annotation.
    public string? RequestId { get; set; }

    public bool ShowRequestId => !string.IsNullOrEmpty(RequestId);
}
