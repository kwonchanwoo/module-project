package com.example.module.barcode.controller;

import com.example.module.barcode.service.BarcodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("barcode")
@RequiredArgsConstructor
public class BarcodeController {
    private final BarcodeService barcodeService;
    @GetMapping()
    public Object generateQRCodeImage(@ModelAttribute("barcode") String barcode) throws Exception{
        return barcodeService.generateQRCodeImage(barcode);
    }

}
