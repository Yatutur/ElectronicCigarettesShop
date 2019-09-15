package com.yatu.electronicCigarettesShop.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.yatu.electronicCigarettesShop.dao.ProductDAO;
import com.yatu.electronicCigarettesShop.dto.ProductDTO;
import com.yatu.electronicCigarettesShop.model.ProductInfo;
 
// @Component: As a Bean.
@Component
public class ProductInfoValidator implements Validator {
 
    @Autowired
    private ProductDAO productDAO;
 
    // This Validator support ProductInfo class.
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == ProductInfo.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
        ProductInfo productInfo = (ProductInfo) target;
 
        // Check the fields of ProductInfo class.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "NotEmpty.productForm.id");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.productForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.productForm.price");
 
        int id = productInfo.getId();
        if (id  > 0) {
//            if (id.matches("\\s+")) {
//                errors.rejectValue("id", "Pattern.productForm.code");
//            } else 
            if(productInfo.isNewProduct()) {
                ProductDTO product = productDAO.findById(id);
                if (product != null) {
                    errors.rejectValue("id", "Duplicate.productForm.id");
                }
            }
        }
    }
 
}
