const button = document.getElementById("loadBtn");

const loading = document.getElementById("loading");

const table = document.getElementById("productTable");

button.addEventListener("click", loadProducts);

function loadProducts(){

    loading.innerHTML = "Loading products...";

    fetch("https://fakestoreapi.com/products")

    .then(response => response.json())

    .then(products =>{

        loading.innerHTML = "";

        table.innerHTML="";

        products.forEach(product=>{

            table.innerHTML += `

            <tr>

                <td>${product.id}</td>

                <td>${product.title}</td>

                <td>${product.price}</td>

                <td>${product.category}</td>

            </tr>

            `;

        });

    })

    .catch(error=>{

        loading.innerHTML="Unable to connect.";

        console.log(error);

    });

}