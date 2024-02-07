package edu.iastate.coms309.flatfinder.history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/histories")
public class HistoryController {

    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public List<History> getAllHistories() {
        return historyService.getAllHistories();
    }

    @GetMapping("/tenant/{tenantName}")
    public List<History> getHistoriesByTenantName(@PathVariable String tenantName) {
        return historyService.getHistoriesByTenantUserName(tenantName);
    }

    @GetMapping("/listing/{listingName}")
    public List<History> getHistoriesByListingName(@PathVariable String listingName) {
        return historyService.getHistoriesByListingListingName(listingName);
    }

    @PostMapping
    public History createHistory(@RequestBody History history) {
        // Determine the history type and create the appropriate history record
        if (history.getHistoryType() == History.HistoryType.LISTING_AND_TENANT) {
            return historyService.createListingHistory(history);
        } else if (history.getHistoryType() == History.HistoryType.TENANT_AND_TENANT) {
            return historyService.createTenantHistory(history);
        } else {
            // Handle an unknown history type or provide appropriate error handling
            throw new IllegalArgumentException("Unknown history type");
        }
    }

    @PutMapping("/{historyId}")
    public History updateHistory(@PathVariable Integer historyId, @RequestBody History history) {
        return historyService.updateHistory(historyId, history);
    }

    @DeleteMapping("/{historyId}")
    public void deleteHistory(@PathVariable Integer historyId) {
        historyService.deleteHistory(historyId);
    }
}