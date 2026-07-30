const button = document.getElementById("loadBtn");

const loading = document.getElementById("loading");

const tableBody = document.getElementById("tableBody");

button.addEventListener("click", loadProducts);

async function loadProducts(){

    loading.innerHTML = "Loading Products...";

    tableBody.innerHTML = "";

    try{

        const response = await fetch(
            "https://fakestoreapi.com/products"
        );

        const products = await response.json();

        loading.innerHTML = "";

        products.forEach(product=>{

            tableBody.innerHTML += `

            <tr>

                <td>${product.id}</td>

                <td>${product.title}</td>

                <td>$${product.price}</td>

                <td>${product.category}</td>

            </tr>

            `;

        });

    }

    catch(error){

        loading.innerHTML="Unable to connect to server.";

        console.log(error);

    }

}