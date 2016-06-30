AJS.toInit(function(){
    jQuery.namespace("JIRA.Admin.Product.EditProductRow");

    var $table = AJS.$("#project-config-products-table");

    function setupCal(dateId) {
        setTimeout(function () {
            Calendar.setup({
                context: $table,
                firstDay: 0,
                inputField: jQuery("#" + dateId).attr("id"),
                align: "BR", 
                singleClick: true,
                ifFormat: "%d.%m.%Y",
                daFormat: "%d.%m.%Y",
                showsTime: false,
                timeFormat: "24",
                useISO8601WeekNumbers: false,
                electric: false,
                onSelect: function (cal)
                { jQuery("#" + dateId).val(cal.date.print(cal.dateFormat)); }
            });
        }, 1000);
    }
    
    JIRA.Admin.Product.EditProductRow = AJS.RestfulTable.EditRow.extend({
        submit: function () {
            var productNameInput = document.getElementById("project-config-product-productName-input");
            var manufacturerInput = document.getElementById("project-config-product-manufacturer-input");
            var manufactureDateInput = document.getElementById("manufactureDateInput");
            var expirationDateInput = document.getElementById("expirationDateInput");
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
        render: function () {
            var data = this.model.toJSON(),
            $el = this.$el;
            $el.html(JIRA.Templates.Product.editProductRow({
                product: data
            }));
            setupCal("manufactureDateInput");
            setupCal("expirationDateInput");
            return this;
        }
    });
});       

