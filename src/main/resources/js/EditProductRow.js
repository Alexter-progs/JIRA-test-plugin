jQuery.namespace("JIRA.Admin.Product.EditProductRow");

JIRA.Admin.Product.EditProductRow = AJS.RestfulTable.EditRow.extend({
    submit: function(){
        var productNameInput = document.getElementById("project-config-product-productName-input");
        var manufacturerInput = document.getElementById("project-config-product-manufacturer-input");
        var manufactureDateInput = document.getElementById("project-config-product-manufactureDate-input");
        var expirationDateInput = document.getElementById("project-config-product-expirationDate-input");
        var descriptionInput = document.getElementById("project-config-product-description-input");

        AJS.$.ajax({
            url: contextPath + '/rest/productresource/1.0/products.json',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                productName: productNameInput.value,
                manufacturer: manufacturerInput.value,
                manufactureDate: manufactureDateInput.value,
                expirationDate: expirationDateInput.value,
                description: descriptionInput.value
            }),
            dataType: 'json'
        }).done(function (data) {
            JIRA.Admin.ProductTable.addRow(data);
            productNameInput.value = '';
            manufacturerInput.value = '';
            manufactureDateInput.value = '';
            expirationDateInput.value = '';
            descriptionInput.value = '';
        });
    },
    render: function(){
        var data = this.model.toJSON(),
            $el = this.$el;
        $el.html(JIRA.Templates.Product.editProductRow({
            product: data
        }));
        return this;
    }
});
