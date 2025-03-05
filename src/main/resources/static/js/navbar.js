$("#searchInput").on("input", function() {
    let filtro = $(this).val().toLowerCase();

    $(".card_locacao").each(function() {
        let id = $(this).data("id").toString().toLowerCase();
        let quarto = $(this).data("quarto").toString().toLowerCase();
        if (id.includes(filtro) || quarto.includes(filtro)) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });

    $(".item-consumo").each(function(){
        let produto = $(this).data("produto").toLowerCase();
        if (produto.includes(filtro)) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });
});